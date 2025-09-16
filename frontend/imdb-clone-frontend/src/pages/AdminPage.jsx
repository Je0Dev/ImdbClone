import React from 'react';
import AdminImport from '../components/AdminImport';

const AdminPage = () => {
    return (
        <div>
            <h1 className="text-4xl font-bold mb-8 text-white">Admin Panel</h1>
            <AdminImport />
            {/* You can add more admin components here in the future */}
        </div>
    );
};

export default AdminPage;
