const puppeteer = require('puppeteer');

async function scrapper(doctorName) {
    console.log('Scrapper function');
    const browser = await puppeteer.launch({
        headless: false,
        //executablePath: '/usr/bin/google-chrome',
        args: ['--no-sandbox', '--disable-setuid-sandbox']
    });
    const page = await browser.newPage();
    await page.goto('https://portal.cfm.org.br/busca-medicos/');
    await page.setViewport({width: 1920, height: 1024});
  
    const [el] = await page.$x('//*[@id="buscaForm"]/div/div[1]/div[1]/div/input');
    await el.type(doctorName);
    const buttonXpath = '//*[@id="buscaForm"]/div/div[4]/div[2]/button';
    const [sendButton] = await page.$x(buttonXpath);
    await sendButton.tap();
    console.log('Search button clicked');
    const imgXpath = '//*[@id="content"]/div[1]/section[2]/div/div/div/div[1]/div/div/div/div[1]/div/img';
    await page.waitForXPath(imgXpath);
    let imageUrl;
    do {
        console.log('Image');
        const [imageElement] = await page.$x(imgXpath);
        const imageSrc = await imageElement.getProperty('src');
        imageUrl = await imageSrc.jsonValue();
    } while (imageUrl === 'https://portal.cfm.org.br/wp-content/themes/portalcfm/assets/images/no-picture.svg');
    browser.close();
    return imageUrl;
}
module.exports = { 
    scrapper 
};
