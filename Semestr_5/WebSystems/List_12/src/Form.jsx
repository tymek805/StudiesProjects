import { useState } from 'react';

function Form() {
    const [formData, setFormData] = useState({
        name: '',
        points: 0,
        author: '',
        isDone: false
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: name === 'points' ? parseInt(value) : value
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
                    points: 0,
                    author: '',
                    isDone: false
                });
            } else {
                alert('Failed to add task');
            }
        } catch (error) {
            console.error('Error adding task:', error);
        }
    };

    return (
        <div>
            <h3>Dodaj nowe zadanie</h3>
            <form onSubmit={handleSubmit}>
                <label>
                    Nazwa zadania:
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
                    Punkty:
                    <input
                        type="number"
                        name="points"
                        value={formData.points}
                        onChange={handleInputChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Autor:
                    <input
                        type="text"
                        name="author"
                        value={formData.author}
                        onChange={handleInputChange}
                        required
                    />
                </label>
                <br />
                <label>
                    Status:
                    <select
                        name="isDone"
                        value={formData.isDone}
                        onChange={(e) =>
                            setFormData((prevData) => ({
                                ...prevData,
                                isDone: e.target.value === 'true'
                            }))
                        }
                    >
                        <option value="false">Not Done</option>
                        <option value="true">Done</option>
                    </select>
                </label>
                <br />
                <button type="submit">Dodaj zadanie</button>
            </form>
        </div>
    );
}

export default Form;
