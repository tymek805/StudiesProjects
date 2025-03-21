const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const cors = require('cors');

const app = express();
const PORT = 5000;

// Middleware
app.use(cors());
app.use(express.json());

// Database connection
const db = new sqlite3.Database('./tasks.db', (err) => {
    if (err) {
        console.error('Database connection error:', err);
    } else {
        console.log('Connected to the SQLite database.');
    }
});

// Create tasks table if it doesn't exist
db.run(`CREATE TABLE IF NOT EXISTS tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    time INTEGER NOT NULL
)`);

// API endpoint to fetch all tasks
app.get('/tasks', (req, res) => {
    db.all('SELECT * FROM tasks', [], (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json(rows);
    });
});

// API endpoint to add a new task
app.post('/tasks', (req, res) => {
    const { name, time } = req.body;
    db.run(
        'INSERT INTO tasks (name, time) VALUES (?, ?)',
        [name, time],
        function (err) {
            if (err) {
                res.status(500).json({ error: err.message });
                return;
            }
            res.json({ id: this.lastID });
        }
    );
});

app.post('/tasks/example', (req, res) => {
    const exampleTasks = [
        { name: 'Analiza matematyczna', time: 120 },
        { name: 'Język angielski C1', time: 90 },
        { name: 'Podstawy programowania', time: 150 },
        { name: 'Bazy danych', time: 180 },
        { name: 'Algebra liniowa', time: 100 }
    ];

    // Function to insert a task
    const insertTask = (task, callback) => {
        db.run(
            `INSERT INTO tasks (name, time) VALUES (?, ?)`,
            [task.name, task.time],
            callback
        );
    };

    // Clear the tasks table first
    db.run('DELETE FROM tasks', function (err) {
        if (err) {
            console.error('Error clearing tasks table:', err.message);
            return res.status(500).json({ error: err.message });
        }

        let completed = 0;

        // Insert example tasks after clearing the table
        exampleTasks.forEach((task) => {
            insertTask(task, (err) => {
                if (err) {
                    console.error('Error inserting task:', err.message);
                    return res.status(500).json({ error: err.message });
                }
                completed++;
                if (completed === exampleTasks.length) {
                    res.json({ message: 'Example tasks added successfully.' });
                }
            });
        });
    });
});


// API endpoint to delete a task by ID
app.delete('/tasks/:id', (req, res) => {
    const { id } = req.params;

    db.run('DELETE FROM tasks WHERE id = ?', [id], function (err) {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }

        if (this.changes === 0) {
            res.status(404).json({ message: 'Task not found' });
        } else {
            res.json({ message: 'Task deleted successfully' });
        }
    });
});


// Start server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
