<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bezos - Auction Ended</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Epilogue:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Jockey+One&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            height: 100vh;
            background-color: #2B67F6;
            color: white;
        }
        header {
            background-color: rgb(255, 255, 255);
            color: rgb(0, 0, 0);
            text-align: center;
            font-size: 20px;
            font-family: 'Jockey One', sans-serif;
        }
        .back-button {
            background-color: #000000;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            position: absolute;
            top: 7%;
            left: 3%;
        }
        .container {
            margin: auto;
            width: 60%;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            color: #000;
            font-family: 'Epilogue', sans-serif;
        }
        h2 {
            font-family: 'Jockey One', sans-serif;
            font-size: 30px;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #003366;
            color: #ffffff;
            border-bottom: 1px solid #ffffff;
        }
        td {
            background-color: #f9f9f9;
        }
        tr:hover { background-color: #f1f1f1; }
        .pay-button {
            background-color: #000;
            color: white;
            padding: 10px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            display: block;
            width: 100px;
            margin: 30px auto;
        }
        .pay-button:hover {
            background-color: #333;
        }
        .info-box {
            background-color: #f2f2f2;
            padding: 10px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <header>
        <h1>BEZOS</h1>
        <button onclick="window.location.href='item.html'" class="back-button">BACK</button>
    </header>
    <div class="container">
        <h2>BIDDING ENDED!</h2>
        <form action="PaymentProcessingServlet" method="post" id="paymentForm">
            <table>
                <tr>
                    <th>Item Description</th>
                    <th>Shipping price</th>
                    <th>Expedited shipping add</th>
                </tr>
                <tr>
                    <td> <%= session.getAttribute("itemDescription") %> </td>
                    <td><%= session.getAttribute("expeditedCost") %> </td>
                    <td><%= session.getAttribute("shippingCost") %> <input type="checkbox" name="expedited" id="expedited"></td>
                </tr>
                <!-- Add more items as needed -->
            </table>
            <div class="info-box">
                <p>Winning Price: <%= session.getAttribute("highestBid") %> </p>
                <p>Highest bidder: <%= session.getAttribute("winningUsername") %></p>     <%-- <%= session.getAttribute("winningBidder") %> --%>
            </div>
            <button type="submit" class="pay-button" id="payButton">Pay Now</button>
            <p id="errorMsg" style="color: red; display: none;">You are not allowed to make this payment.</p>
               
        </form>
    </div>
    <script type="text/javascript">
    
   
        
        var currentUser = <%= session.getAttribute("username") %>;
        var winningBidder = <%= session.getAttribute("winningUsername")%>;
        
        window.onload = function() {
            var payButton = document.getElementById('payButton');
            var errorMsg = document.getElementById('errorMsg');
            var form = document.querySelector('form');

            form.onsubmit = function(event) {
                if (currentUser !== winningBidder) {
                    // Prevent form submission
                    event.preventDefault();
                    // Disable the pay button
                    payButton.disabled = true;
                    payButton.innerText = 'Not Allowed';
                    // Display the error message
                    errorMsg.style.display = 'block';
                }
            };
        };
    </script>
</body>
</html>
