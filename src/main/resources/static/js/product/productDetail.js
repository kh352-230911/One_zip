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

function submitTo(destination) {
    var form = document.querySelector('form[name="productFrm"]');
    if (destination === 'gift') {
        // form.action = '${contextPath}/product/productGiftInfo.do';
        location.href = `${contextPath}/product/productGiftInfo.do`;
    } else if (destination === 'purchase') {
        // form.action = '${contextPath}/product/productPurchaseInfo.do';
        location.href = `${contextPath}/product/productPurchaseInfo.do`;
    }
    form.method = 'POST'; // 명시적으로 POST 요청으로 설정
    form.submit();
}

