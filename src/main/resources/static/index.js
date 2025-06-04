const getProducts = async () => {
   return fetch("/api/products")
    .then(r => r.json());
}

const createHtmlEl = (product) => {
    const template = `
        <h4>${product.name}</h4>
        <p>${product.description}</p>
        <span>${product.price}</span>
    `;
    const el = document.createElement('li');
    el.innerHTML = template.trim();
    return el;
}

(() => {
    const productsList = document.querySelector('.products');
     getProducts()
        .then(products => products.map(createHtmlEl))
        .then(htmlProds =>  htmlProds.forEach(el => productsList.appendChild(el)))
        ;
})();
