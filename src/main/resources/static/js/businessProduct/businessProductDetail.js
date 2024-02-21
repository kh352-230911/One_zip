document.querySelector("#plus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantity = document.querySelector("#product-quantity")
    product_quantity.value = parseInt(product_quantity.value) + 1;
});

document.querySelector("#minus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantity = document.querySelector("#product-quantity")
    if(product_quantity.value != 1){
        product_quantity.value = parseInt(product_quantity.value) - 1;
    }
});

document.querySelector("#plus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    // const product_quantity = parseInt(product_quantityEle.innerText); // innerHTML 대신 innerText 사용
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);

    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    productTotalPriceEle.value = productSellPrice * product_quantity;
});

document.querySelector("#minus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);

    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    productTotalPriceEle.value = productSellPrice * product_quantity;
});

document.querySelector("#product-quantity").addEventListener('input', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    if(parseInt(product_quantityEle.value) < 1){
        product_quantityEle.value = 1;
    }
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);

    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    if(isNaN(parseInt(product_quantityEle.value))){
        productTotalPriceEle.value = productSellPrice;
    }
    else{
        productTotalPriceEle.value = productSellPrice * product_quantity;
    }
});