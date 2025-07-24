import React from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { deleteContact } from '../api/ContactService';

const Contact = ({ contact, getAllContacts, currentPage }) => {
  const navigate = useNavigate();

  const {
    id,
    photoUrl,
    name,
    title,
    email,
    address,
    phone,
    status
  } = contact;

  const handleDelete = async () => {
    if (!window.confirm("Voulez-vous vraiment supprimer ce contact ?")) return;
    try {
      await deleteContact(id);
      toast.success("✅ Contact supprimé !");
      if (getAllContacts) {
        getAllContacts(currentPage);
      }
    } catch (error) {
      toast.error("❌ Erreur lors de la suppression !");
    }
  };

  return (
    <div className="contact_item">
      <div className="contact_header">
        <div className="contact_image">
          <img src={photoUrl || '/default.png'} alt={name || 'Unnamed'} />
        </div>
        <div className="contact_details">
          <p className="contact_name">{name?.substring(0, 15) || 'No Name'}</p>
          <p className="contact_title">{title || 'No Title'}</p>
        </div>
      </div>
      <div className="contact_body">
        <p><i className="bi bi-envelope"></i> {email?.substring(0, 20) || 'No Email'}</p>
        <p><i className="bi bi-geo"></i> {address || 'No Address'}</p>
        <p><i className="bi bi-telephone"></i> {phone || 'No Phone'}</p>
        <p>
          {status === 'Active'
            ? <i className='bi bi-check-circle'></i>
            : <i className='bi bi-x-circle'></i>}
          {' '}{status || 'Unknown'}
        </p>
      </div>
      <div className="contact_footer">
        <button onClick={() => navigate(`/contacts/${id}`)} className="btn btn-primary">
          📄 Détails
        </button>
        <button onClick={handleDelete} className="btn btn-danger">
          🗑 Supprimer
        </button>
      </div>
    </div>
  );
};

export default Contact;
