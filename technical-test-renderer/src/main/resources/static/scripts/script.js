async function handleChangePage(step) {
    let filtersPageInput = document.getElementById('filters.page');
    filtersPageInput.value = parseFloat(filtersPageInput.value) + step;
}