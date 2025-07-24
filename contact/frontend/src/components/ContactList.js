import React from 'react';
import Contact from "./Contact";

const ContactList = ({ data, currentPage, getAllContacts }) => {
  const handlePageChange = (page) => {
    if (page >= 0 && page < data.totalPages) {
      getAllContacts(page);
    }
  };

  return (
    <main className='main'>
      {data?.content?.length === 0 && (
        <div>No Contacts. Please add a new contact</div>
      )}

      <ul className='contact__list'>
        {data?.content?.length > 0 &&
          data.content.map(contact => (
            <Contact
              contact={contact}
              key={contact.id}
              getAllContacts={getAllContacts}
              currentPage={currentPage}
            />
          ))}
      </ul>

      {data?.content?.length > 0 && data.totalPages > 1 && (
        <div className='pagination'>
          <button
            onClick={() => handlePageChange(currentPage - 1)}
            className={currentPage === 0 ? 'disabled' : ''}
            disabled={currentPage === 0}
          >
            &laquo;
          </button>

          {[...Array(data.totalPages).keys()].map((page) => (
            <button
              key={page}
              onClick={() => handlePageChange(page)}
              className={currentPage === page ? 'active' : ''}
            >
              {page + 1}
            </button>
          ))}

          <button
            onClick={() => handlePageChange(currentPage + 1)}
            className={currentPage === data.totalPages - 1 ? 'disabled' : ''}
            disabled={currentPage === data.totalPages - 1}
          >
            &raquo;
          </button>
        </div>
      )}
    </main>
  );
};

export default ContactList;
