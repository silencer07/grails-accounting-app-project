<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Create T-Account Entry</title>
    </head>
    <body>
        <div id="list-transactionTemp" class="content scaffold-list" role="main">
            <h1>Create T-Account Entry</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="content scaffold-list" role="main">
                <div class="fieldcontain">
                    <label for="description">Ref. Description</label>
                    <g:textField name="reference"/>
                </div>
                <div class="fieldcontain required">
                    <label for="documentDate">Date
                        <span class="required-indicator">*</span>
                    </label> <g:datePicker name="documentDate" value="" default="${new Date()}"/>
                </div>
                <br/>
                <table>
                    <tr>
                        <th>Particular</th>
                        <th>Posting Key</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Comments</th>
                    </tr>
                    <tr>
                        <td><g:select name="accountId" from="${Account.list()}" optionKey="id"/></td>
                        <td><g:select name="postingKey" from="${Side.values()}"/></td>
                        <td><g:textField name="amount"/></td>
                        <td><g:textField name="description"/></td>
                        <td><g:textField name="comments"/></td>
                    </tr>
                </table>
            </div>
    </body>
</html>