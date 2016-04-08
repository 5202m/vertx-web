// Booklist data array for filling in info box
var bookListData = [];

//DOM Ready =============================================================
$(document).ready(function () {

    // Populate the user table on initial page load
    populateTable();

    // Username link click
    //$('#userList table tbody').on('click', 'td a.linkshowuser', showUserInfo);

    // Add User button click
    //$('#btnAddUser').on('click', addUser);

    // Delete User link click
    //$('#userList table tbody').on('click', 'td a.linkdeleteuser', deleteUser);

});

// Functions =============================================================

// Fill table with data
function populateTable() {

    // Empty content string
    var tableContent = '';

    // jQuery AJAX call for JSON
    $.getJSON('/booksList', function (data) {

        // Stick our user data array into a userlist variable in the global object
    	bookListData = data;

        // For each item in our JSON, add a table row and cells to the content string
        $.each(data, function () {
            tableContent += '<tr>';
            tableContent += '<td><a href="#" class="linkshowuser" rel="' + this.name + '" title="Show Details">' + this.name + '</a></td>';
            tableContent += '<td>' + this.author + '</td>';
            tableContent += '</tr>';
        });

        // Inject the whole content string into our existing HTML table
        $('#bookList table tbody').html(tableContent);
    });
};
