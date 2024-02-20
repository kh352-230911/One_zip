document.querySelectorAll("tr[data-question-id]").forEach((tr) => {
    tr.addEventListener('click', (e) => {
        const td = e.target;
        const tr = td.parentElement;
        const {questionId} = tr.dataset;
        location.href = `${contextPath}businessmanagement/businessqanswerdetail.do?id=${questionId}`;
    });
});