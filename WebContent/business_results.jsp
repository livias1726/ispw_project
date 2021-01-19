 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="logic.presentation.bean.BusinessBean"
		  import="logic.presentation.bean.CountryBean"
		  import="logic.presentation.bean.BusinessInCountryBean"
		  import="logic.application.control.ViewResultsControl"
		  import="logic.application.control.ViewBusinessControl"%>
<%@page errorPage="WEB-INF/error.jsp"%>

<!DOCTYPE html>

<jsp:useBean id="countryBean" scope="session" class="logic.presentation.bean.CountryBean"/>
<jsp:setProperty name="countryBean" property="*"/>

<jsp:useBean id="businessBean" scope="session" class="logic.presentation.bean.BusinessBean"/>
<jsp:setProperty name="businessBean" property="*"/>

<jsp:useBean id="businessInCountryBean" scope="session" class="logic.presentation.bean.BusinessInCountryBean"/>
<jsp:setProperty name="businessInCountryBean" property="*"/>

<%Class.forName("com.mysql.cj.jdbc.Driver");%>

<%if (request.getParameter("business") != null){
	businessBean.setName(request.getParameter("business"));
	String redirectURL = "http://localhost:8080/WorldWideJob/businessDetails.jsp";
	response.sendRedirect(redirectURL); 
}%>
    			 
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<link rel="icon" href="icons/main_icon.png">
	    <link href="css/style.css" rel="stylesheet">
	
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
		<title>WorldWideJob</title>
	</head>	
	<body>
		<jsp:include page="WEB-INF/toolbar.jsp"/>
		<div>
			<form action="business_results.jsp" name="businessresultform" method="POST">
	    		<div style="background-color:#AED6F1; overflow:hidden">
	    		
	    			<!-- BUSINESS RESEARCH -->
		    		<%if(businessBean.getCategory() == null) {%>
		    			<p><input class="result_label" name="resultLbl" value="<%=countryBean.getName()%>"></p>
		    			<p><input class="order_by" type="text" name="orderby" value="Order by:" disabled style="background-color:lightgrey">
			    		<select class="order_select" id="order" name="orderselect" style="background-color:lightgrey">
			    			<option>Earnings</option>
			    			<option>Costs</option>
			    		</select></p>
			    		<div>
			    			<fieldset class="result_box">
					    		<legend class="research_title">BUSINESSES</legend>
					    			<ul id="res" style="list-style-type:none;">
					    			<%for(BusinessInCountryBean i: ViewBusinessControl.getInstance().retrieveBusinessesByCountry(countryBean)){%>
					    				<li><button class="result" type="submit" name="business" value="<%=i%>"> <%=i.getName()+ " - " +i.getCountry().getName()%> </button></li>
					    			<%}%>    
					    			</ul>
				    		</fieldset>	
			    		</div>		    			    					
		    			<fieldset class="filter_box">
				    		<legend style="font-weight:bold">Category:</legend>
				    			<%for(BusinessBean i: ViewBusinessControl.getInstance().retrieveBusinesses()){%>
				    				<input type="checkbox" id="cat1" name="cat1" value="<%=i.getCategory()%>" onclick="filterResults()">
  									<label for="cat1"><%=i.getCategory()%></label><br>
				    			<%}%>
			    		</fieldset>
			    		
			    	<!-- COUNTRY RESEARCH -->
		    		<%}else if(countryBean.getName() == null) {%>
		    			<p><input class="result_label" name="resultLbl" value="<%=businessBean.getCategory()%>"></p>
		    			<p><input class="order_by" type="text" name="orderby" value="Order by:" disabled style="background-color:lightgrey">
			    		<select class="order_select" id="order" name="orderselect" style="background-color:lightgrey">
			    			<option>Earnings</option>
			    			<option>Costs</option>
			    		</select></p>
			    		<div>
			    			<fieldset class="result_box">
					    		<legend class="research_title">BUSINESSES</legend>
					    			<ul id="res" style="list-style-type:none;">
					    			<%businessInCountryBean.setCategory(businessBean.getCategory());
					    			  for(BusinessInCountryBean i: ViewBusinessControl.getInstance().retrieveBusinessesByCategory(businessInCountryBean)){%>
					    				<li><button class="result" type="submit" name="business" value="<%=i%>"> <%=i.getName()+ " - " +i.getCountry().getName()%> </button></li>
					    			<%}%>    
					    			</ul>
				    		</fieldset>	
			    		</div>		    			    					
		    			<fieldset class="filter_box">
				    		<legend style="font-weight:bold">Countries:</legend>
					    		<%for(String i: ViewResultsControl.getInstance().retrieveCountries()){%>
				    				<input type="checkbox" id="cat1" name="cat1" value="<%=i%>" onclick="filterResults()">
  									<label for="cat1"><%=i%></label><br>
				    			<%}%>
			    		</fieldset>
		    	
			    	<!-- BUSINESS AND COUNTRY RESEARCH -->
		    		<%}else{%>
		    			<p><input class="result_label" name="resultLbl" value="<%=businessBean.getCategory() + " in " +countryBean.getName()%>"></p>
		    			<p><input class="order_by" type="text" name="orderby" value="Order by:" disabled style="background-color:lightgrey">
			    		<select class="order_select" id="order" name="orderselect" style="background-color:lightgrey">
			    			<option>Earnings</option>
			    			<option>Costs</option>
			    		</select></p>
			    		<div>
			    			<fieldset class="result_box">
					    		<legend class="research_title">BUSINESSES</legend>
					    			<ul id="res" style="list-style-type:none;">
					    			<%businessInCountryBean.setCategory(businessBean.getCategory());
					    			  for(BusinessInCountryBean i: ViewBusinessControl.getInstance().retrieveBusinesses(countryBean, businessInCountryBean)){%>
					    				<li><button class="result" type="submit" name="business" value="<%=i%>"> <%=i.getName()+ " - " +i.getCountry().getName()%> </button></li>
					    			<%}%>    
					    			</ul>
				    		</fieldset>	
			    		</div> 
			    	<%}%>     		
	    		</div>
	    	</form>	
	    </div>
	</body>
	<script src="js/business_results.js"></script>
</html>