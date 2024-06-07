const { scrapper } = require('./scrapper.js');
const express = require('express');
const app = express();
const port = process.env.PORT || 8080;

// Middleware para processar JSON
app.use(express.json());

app.get('/', (req, res) => {
    res.send('Hello World');
});

app.post('/medico', async (req, res) => {
    const data = req.body;
    const doctorName = data.doctorName;
    try {
        const imageUrl = await scrapper(doctorName);
        console.log(imageUrl);
        res.status(201).send({ imageUrl });
    } catch (error) {
        res.status(500).send({ error: 'Something went wrong' });
    }
});

app.post('/auth', async (req, res) => {
    const data = req.body;
    res.send('Hello World');
});

app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
