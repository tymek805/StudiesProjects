import { useState } from 'react';

function Form() {
    const [formData, setFormData] = useState({
        name: '',
        time: 0
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: name === 'time' ? parseInt(value) : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:5000/tasks', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                alert('Task added successfully!');
                setFormData({
                    name: '',
                    time: 0
                });
                window.location.reload()
            } else {
                alert('Failed to add task');
            }
        } catch (error) {
            console.error('Error adding task:', error);
        }
    };

    const addExampleTasks = async () => {
        try {
            const response = await fetch('http://localhost:5000/tasks/example', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                alert('Example tasks added successfully!');
                window.location.reload();
            } else {
                alert('Failed to add example tasks');
            }
        } catch (error) {
            console.error('Error adding example tasks:', error);
        }
        
    };

    return (
        <div>
            <button onClick={addExampleTasks}>Przywróć do domyślnych</button>
            <h3>Dodaj nowy przedmiot</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Nazwa przedmiotu:
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Czas:
                    <input
                        type="number"
                        name="time"
                        value={formData.time}
                        onChange={handleInputChange}
                        required
                    />
                </label>
                <br />
                <button type="submit">Dodaj przedmiot</button>
            </form>
        </div>
    );
}

export default Form;
