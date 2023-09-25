function populateTable(list) {
    const tableBody = document.querySelector('#table-body');

    for (const object of list) {
        // Create a new table row element.
        const tableRow = document.createElement('tr');

        // Create a new table data element for each property of the object.
        for (const property in object) {
            const tableData = document.createElement('td');
            tableData.textContent = object[property];

            // Append the table data element to the table row element.
            tableRow.appendChild(tableData);
        }

        // Append the table row element to the table body element.
        tableBody.appendChild(tableRow);
    }
}
