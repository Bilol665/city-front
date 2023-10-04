// Get references to the input and category suggestions
const searchInput = document.getElementById('search-input');
const categorySuggestions = document.getElementById('category-suggestions');

// Map of keywords and their corresponding categories
const keywordToCategoryMap = {
    laptops: 'Laptops',
    phones: 'Phones',
    monitors: 'Monitors',
    // Add more keywords and categories as needed
};

// Function to display category suggestions based on user input
function displayCategorySuggestions() {
    const inputValue = searchInput.value.trim().toLowerCase();

    // Clear the category suggestions
    categorySuggestions.innerHTML = '';

    // Check if the input matches any keywords
    for (const keyword in keywordToCategoryMap) {
        if (inputValue.length > 0) {
            const category = keywordToCategoryMap[keyword];
            const categoryItem = document.createElement('div');
            const formElement = document.createElement('form');
            formElement.setAttribute('action','/flat/search');
            formElement.setAttribute('method','get');
            categoryItem.appendChild(formElement);
            categoryItem.textContent = `Search in ${category}`;
            categoryItem.addEventListener('click', () => {
                initiateCategorySearch(category);
            });
            categorySuggestions.appendChild(categoryItem);
        }
    }

    // Show the category suggestions if there are matches
    if (categorySuggestions.children.length > 0) {
        categorySuggestions.style.display = 'block';
    } else {
        // Hide the category suggestions if there are no matches
        categorySuggestions.style.display = 'none';
    }
}

// Event listener for input changes
searchInput.addEventListener('input', displayCategorySuggestions);

// Function to initiate a search within a category
function initiateCategorySearch(category) {
    // Replace this with your actual search logic within the specified category
    alert(`Performing a search in category: ${category}`);
}
