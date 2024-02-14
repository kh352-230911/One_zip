document.querySelectorAll("tr[data-businessproduct-id]").forEach((tr) => {
    tr.addEventListener('click', (e) => {
        const td = e.target;
        const tr = td.parentElement;
        const {businessproductId} = tr.dataset;
        location.href = `${contextPath}businessproduct/businessproductdetail.do?id=${businessproductId}`;
    });
});