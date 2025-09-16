import React, { useState } from 'react';
import movieService from '../services/movieService';
import toast from 'react-hot-toast';

const AdminImport = () => {
    const [title, setTitle] = useState('');
    const [isImporting, setIsImporting] = useState(false);

    const handleImport = async (e) => {
        e.preventDefault();
        setIsImporting(true);
        const importPromise = movieService.importMovie(title);

        toast.promise(importPromise, {
            loading: `Importing "${title}"...`,
            success: (response) => {
                setTitle('');
                setIsImporting(false);
                return `Successfully imported "${response.data.title}"!`;
            },
            error: (err) => {
                setIsImporting(false);
                return err.response?.data?.message || 'Could not find movie.';
            },
        });
    };

    return (
        <div className="bg-brand-light-dark p-6 rounded-lg shadow-lg">
            <h3 className="text-2xl font-bold mb-4 text-brand-yellow">Import Movie from OMDb</h3>
            <form onSubmit={handleImport} className="flex gap-2">
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="Enter movie title"
                    required
                    className="flex-grow shadow appearance-none border rounded py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
                />
                <button
                    type="submit"
                    disabled={isImporting}
                    className="bg-brand-yellow hover:bg-yellow-500 text-black font-bold py-2 px-4 rounded disabled:opacity-50"
                >
                    {isImporting ? 'Importing...' : 'Import'}
                </button>
            </form>
        </div>
    );
};

export default AdminImport;
