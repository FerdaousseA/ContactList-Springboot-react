package com.example.contactapi.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.contactapi.constant.Constant;
import com.example.contactapi.domain.Contact;
import com.example.contactapi.repo.ContactRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Slf4j

@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor

public class ContactService {
	private static final Logger log = LoggerFactory.getLogger(ContactService.class);

	@Autowired
    private ContactRepo contactRepo;
	
    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Contact getContact(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public Contact createContact(Contact contact) {
        return contactRepo.save(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepo.delete(contact);
    }

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Saving picture for user ID: {}", id);
        Contact contact = getContact(id);
        String photoUrl = photoFunction.apply(id, file);
        contact.setPhotoUrl(photoUrl);
        contactRepo.save(contact);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename ->
        Optional.ofNullable(filename)
                .filter(name -> name.contains("."))
                .map(name -> "." + name.substring(name.lastIndexOf('.') + 1))
                .orElse(".jpg");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, file) -> {
        String filename = id + fileExtension.apply(file.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(Constant.PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Path targetLocation = fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/contacts/file/")
                    .path(filename)
                    .toUriString();
        } catch (Exception exception) {
            log.error("Error while saving image", exception);
            throw new RuntimeException("Unable to save image", exception);
        }
    };
}
