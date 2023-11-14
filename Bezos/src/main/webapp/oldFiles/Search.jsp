<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bezos - Search Page</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="styles/SearchStyle.css">
    <title>Welcome</title>
</head>
<body>
    <header>
        <h1>BEZOS</h1>
        <button onclick="window.location.href='Main.html'" class="back-button">BACK</button>
    </header>
    <div class="flex-container">
        <h1>Search</h1>
        <form>
            <label for="search">Search</label>
            <input type="text" id="search" name="search" placeholder="Enter Search">
            <button type="button" onclick="addRow()">Search</button>
        </form>
    </div>
    <div class="main">
        <div class="tableContainer">
            <table id="myTable" border="1">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Current Price</th>
                        <th>Auction Type</th>
                        <th>Remaining Time</th>
                        <th>Select</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- JSP logic to generate table rows based on server response --%>
                    <%
                        // Dummy data for demonstration
                        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
                        double initialPrice = 100.0;
                        for (String itemName : itemNames) {
                            // Calculate current price based on time elapsed
                            double currentTime = System.currentTimeMillis();
                            double elapsedMinutes = (currentTime - request.getSession().getCreationTime()) / (60 * 1000);
                            double currentPrice = Math.max(0, initialPrice - (initialPrice * 0.1 * elapsedMinutes));

                            // Display table row
                    %>
                            <tr>
                                <td><%= itemName %></td>
                                <td><%= String.format("%.2f", currentPrice) %></td>
                                <td>Auction Type</td>
                                <td>Remaining Time</td>
                                <td>Select</td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <button class="bid-button" onclick="bid()">BID</button>
           
        </div>
    </div>
    <script>
        function addRow() {
            var inputValue = document.getElementById("search").value;
            // Perform an AJAX request to the server to add a row
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    // Update the table with the received HTML content
                    document.getElementById("myTable").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "SearchServlet?search=" + inputValue, true);
            xhttp.send();
        }
        function bid() {
            // Perform actions when the BID button is clicked
            // You can add logic to handle bidding, e.g., update user's bid in the database
        }
    </script>
</body>
</html>


