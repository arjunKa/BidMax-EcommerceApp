<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.app.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bezos - Receipt</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Epilogue:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Jockey+One&display=swap" rel="stylesheet">
    <style>
          body {
            margin: 0;
            padding: 0;
            font-family: 'Epilogue', sans-serif;
            display: flex;
            flex-direction: column;
            height: 100vh;
            background-color: #2B67F6;
            color: white;
        }

        header {
            background-color: #fff;
            color: #000;
            text-align: center;
            font-size: 24px;
            font-family: 'Jockey One', sans-serif;
        }

        header h1 {
            margin: 0;
            padding: 20px 0;
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
            display: flex;
            justify-content: space-around;
            align-items: center;
            margin: auto;
            padding: 40px;
            background-color: #0056b3;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 900px;
        }

        .section {
            flex: 1;
            margin: 0 20px;
        }
        

        .divider {
            border-left: 2px solid rgb(0, 0, 0);
            height: 100%;
        }

     

        input[type=text] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
        }

      .submit-btn-wrapper {
    text-align: center; /* Center the button wrapper horizontally */
    margin-top: 200px; /* Add some space above the button */
    width: 100%; /* Ensure the wrapper takes full width */
}

       .submit-btn {
    background-color: #000000;
    color: white;
    padding: 10px 15px; /* Adjust padding as needed */
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    display: block; /* Change to block to take the full width */
    width: auto; /* Set width to auto so it expands as needed based on text */
     margin-top: 480px; /* Adjust this value as needed to push the button down */
    margin-bottom: 40px; /* If you want to push it further down */
    margin-left: auto; /* Center button horizontally */
    margin-right: auto; /* Center button horizontally */
}

   h2 {
            font-size: 28px;
            margin-bottom: 15px;
        }
        .submit-btn:hover {
            background-color: #333333;
        }

        @media (max-width: 768px) {
            .container {
                flex-direction: column;
                padding: 20px;
            }

            .divider {
                border-left: none;
                border-top: 2px solid rgb(0, 0, 0);
                width: 100%;
                height: auto;
                margin: 20px 0;
            }

            .section {
                margin: 0;
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>BEZOS</h1>
        <button onclick="window.location.href='Receipt.jsp'" class="back-button">BACK</button>
    </header>
    
    <div class="container">
        
            <div class="section">
                <h2>Receipt</h2>
                <%
                    User winnerDetails = (User) session.getAttribute("winnerDetails");
                    Integer itemId = (Integer) session.getAttribute("itemId");
                    String shippingTime = (String) session.getAttribute("shippingTime");
                    Double totalCost = (Double) session.getAttribute("totalCost");
                %>
                <label for="fname">First Name:</label>
                <input type="text" id="fname" name="fname" value="<%= winnerDetails!= null ?winnerDetails.getFirstName(): ""%>" readonly>
                <label for="lname">Last Name:</label>
                <input type="text" id="lname" name="lname" value="<%= winnerDetails  != null ? winnerDetails.getLastName() : "" %>" readonly>
                <label for="address">Street address:</label>
                <input type="text" id="address" name="address" value="<%= winnerDetails != null ?winnerDetails.getAddress(): ""%>" readonly>
                <label for="province">Province:</label>
                <input type="text" id="province" name="province" value="<%= winnerDetails != null ?winnerDetails.getCity(): ""%>" readonly>
                <label for="country">Country:</label>
                <input type="text" id="country" name="country" value="<%= winnerDetails != null ?winnerDetails.getCountry(): "" %>" readonly>
                <label for="postal">Postal Code:</label>
                <input type="text" id="postal" name="postal" value="<%=  winnerDetails!= null ?winnerDetails.getPostalCode() : ""%>" readonly>
                <label for="itemId">Item ID:</label>
                <input type="text" id="itemId" name="itemId" value="<%= itemId != null ? itemId.toString() : "" %>" readonly>
				<label for="totalPaid">Total Paid:</label>
				<input type="text" id="totalPaid" name="totalPaid" value="<%= totalCost != null ? totalCost.toString() : "" %>" readonly>
</div>
<div class="divider"></div>
<div class="section">
    <h2>Shipping Details</h2>
    <p>The Item will be shipped in <%= shippingTime %> days.</p>


    <button type="button" class="submit-btn" onclick="window.location.href='Main.html'">Back to Main Page</button>

</div>

</div>

<script>
    // Your JavaScript here
</script>
</body>
</html>
                
                
                
                
                
                
                
                
                
                