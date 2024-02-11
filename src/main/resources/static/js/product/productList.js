document.querySelectorAll("tr[data-product-id]").forEach((tr) => {
    tr.addEventListener('click', (e) => {
        const td = e.target;
        const tr = td.parentElement;
        const {productId} = tr.dataset;
        location.href = `${contextPath}product/productDetail.do?id=${productId}`;
    });
});
