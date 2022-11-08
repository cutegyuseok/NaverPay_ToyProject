<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.naverpay.util.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>SHOPPINGLIST</title>
    <link href="/resources/css/style.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <style>
        .q{
            font-family: '나눔고딕',NanumGothic,'맑은고딕',MalgunGothic,'돋움',Dotum,Helvetica,sans-serif;
            font-size: 12px;
            list-style: none;
            color: #000;
            text-align: center;
            display: block;
            overflow: hidden;
            max-width: 100%;
            white-space: nowrap;
            text-overflow: ellipsis;
            margin-bottom: 5px;
        }
        .startDate{
            width: max-content;
        }
        .card{
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<header>
    <jsp:include page="../../layout/header.jsp"/>
</header>

<%@include file="../../message/showMessage.jsp"%>
<%
    showMessage(request, response, "login", Status.FAIL);
%>

<main>
    <h1>Shopping List</h1>
    <div>
        <form method="post" action="/members/shopping">
            <div class="form__list">
                <label class="startDate" for="startDate">START DATE </label>
                <input type="text" id="startDate" name="startDate" placeholder="yyyy.mm.dd" required/>
            </div>

            <div class="form__list">
                <label for="endDate">END DATE</label>
                <input type="text" id="endDate" name="endDate" placeholder="yyyy.mm.dd" required/>
            </div>
            <input type="submit" name="submit" value="Submit">
        </form>
    </div>
    <div class="goods_info">
        <c:forEach var="shoppingListDTO" items="${shoppingList}">
            <div class="card">
                <h5 class="card-header">${shoppingListDTO.getsDate()}</h5>
                <div class="card-body">
                    <h5 class="card-title">${shoppingListDTO.getsTitle()}</h5>
                    <p class="card-text"><p>상품금액 ${shoppingListDTO.getsPayment()}원</p>
                    <p class="q">${shoppingListDTO.getsStatus()}</p>
                    <p class="q">판매자 ${shoppingListDTO.getSeller()}  ${shoppingListDTO.getSellerPhoneNumber()}</p></p>
                    <a href="/members/shoppingDetail?sId=${shoppingListDTO.sId}" class="btn btn-secondary">세부 정보</a>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<footer class="main__nav__next">
    <jsp:include page="../../layout/footer.jsp"/>
</footer>
</body>
</html>
