import { useEffect, useState } from 'react';

function Listazadan() {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        fetchTasks();
    }, []);

    const fetchTasks = async () => {
        try {
            const response = await fetch('http://localhost:5000/tasks');
            if (!response.ok) {
                throw new Error('Failed to fetch tasks');
            }
            const data = await response.json();
            setTasks(data);
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    };

    const deleteTask = async (id) => {
        try {
            const response = await fetch(`http://localhost:5000/tasks/${id}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Task deleted successfully!');
                setTasks((prevTasks) => prevTasks.filter((task) => task.id !== id));
            } else {
                alert('Failed to delete task');
            }
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    };

    const downloadTasksAsJSON = () => {
        const jsonBlob = new Blob([JSON.stringify(tasks, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(jsonBlob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'tasks.json';
        link.click();
        URL.revokeObjectURL(url);
    };

    return (
        <div>
            <button onClick={downloadTasksAsJSON} style={{ marginBottom: '10px' }}>
                Pobierz JSON
            </button>
            <table border="1" style={{ borderCollapse: 'collapse', width: '100%' }}>
                <thead>
                    <tr>
                        <th>Przedmiot</th>
                        <th>Czas [min]</th>
                        <th>Akcje</th>
                    </tr>
                </thead>
                <tbody>
                    {tasks.length > 0 ? (
                        tasks.map((zadanie) => (
                            <tr key={zadanie.id}>
                                <td>{zadanie.name}</td>
                                <td>{zadanie.time}</td>
                                <td>
                                    <button onClick={() => deleteTask(zadanie.id)}>Usuń</button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="3" style={{ textAlign: 'center' }}>Brak danych</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default Listazadan;
