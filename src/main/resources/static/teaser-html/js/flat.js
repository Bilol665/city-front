let ascendingOrder = true; // Flag to track sorting order

function sortTable(columnIndex) {
    if (columnIndex === 0) { // Check if it's the "Number" column (0-based index)
        const table = document.getElementById("myTable");
        const tbody = table.querySelector("tbody");
        const rows = Array.from(tbody.getElementsByTagName("tr"));

        rows.sort((a, b) => {
            const aValue = parseInt(a.getElementsByTagName("td")[columnIndex].textContent);
            const bValue = parseInt(b.getElementsByTagName("td")[columnIndex].textContent);

            if (ascendingOrder) {
                return aValue - bValue;
            } else {
                return bValue - aValue;
            }
        });

        // Toggle the sorting order flag
        ascendingOrder = !ascendingOrder;

        // Remove existing rows from the tbody
        rows.forEach(row => tbody.removeChild(row));

        // Append the sorted rows back to the tbody
        rows.forEach(row => tbody.appendChild(row));
    }
}
