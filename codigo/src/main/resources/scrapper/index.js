const express = require('express');
const puppeteer = require('puppeteer');
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
    console.log(data, 'asdasdadasdasdasdasdasdasdad');
    console.log(doctorName, 'kkkkkkkkkkkkkkkkkkkkkk');
    // const { doctorName } = await JSON.parse(data);
    try {
        const imageUrl = await scrapper(doctorName);
        console.log(imageUrl);
        res.status(201).send({ imageUrl });
    } catch (error) {
        res.status(500).send({ error: 'Something went wrong' });
    }
});

app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});

async function scrapper(doctorName) {
    console.log("To tristinho", doctorName);
    let count =0;
    const browser = await puppeteer.launch({
        executablePath: '/usr/bin/google-chrome',
        args: ['--no-sandbox', '--disable-setuid-sandbox']
    });
    console.log("DEPRESSAOOOOO");
    const page = await browser.newPage();
    await page.goto('https://portal.cfm.org.br/busca-medicos/');
    await page.setViewport({width: 1920, height: 1024});
    console.log("DEPRESSAOOOOO 2222222");

    const [el] = await page.$x('//*[@id="buscaForm"]/div/div[1]/div[1]/div/input');
   // const inputField = await page.$x('//*[@id="buscaForm"]/div/div[1]/div[1]/div/input');
    await el.type(doctorName);
    console.log("DEPRESSAOOOOO 3333333");
    const buttonXpath = '//*[@id="buscaForm"]/div/div[4]/div[2]/button';
    const [sendButton] = await page.$x(buttonXpath);
    await sendButton.tap();
    console.log("DEPRESSAOOOOO 4444444");
    const imgXpath = '//*[@id="content"]/div[1]/section[2]/div/div/div/div[1]/div/div/div/div[1]/div/img';
    await page.waitForXPath(imgXpath);
    console.log("DEPRESSAOOOOO 5555555");
    let imageUrl;
    do {
        // new Promise(r => setTimeout(r, 100));
        const [imageElement] = await page.$x(imgXpath);
        const imageSrc = await imageElement.getProperty('src');
        imageUrl = await imageSrc.jsonValue();
        console.log(imageUrl, count++);
    } while (imageUrl === 'https://portal.cfm.org.br/wp-content/themes/portalcfm/assets/images/no-picture.svg');
    browser.close();
    console.log("Fechou a pagina sem erro seloko sabe muito");
    return imageUrl;
}
async function run() {
    console.log( await scrapper('Drauzio Varella'));
}
