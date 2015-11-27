<%@page import="com.admu.accountinggroup.domain.*" %>
<%@page import="com.admu.accountinggroup.*" %>
<%@page import="com.admu.accountinggroup.utils.DateUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>T-Account Pending Entries</title>
    </head>
    <body>
            <div class="content scaffold-list" role="main">
                <h1>T-Account Pending Entries</h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="create" action="entryAdd">Add T-Account Entry</g:link></li>
                        <li><g:link class="create" action="sync">Sync Cache to Persistent Storage</g:link></li>
                    </ul>
                </div>
                <br/>
                <g:if test="${documents}">
                    <table border="1">
                        <tr>
                            <th>Date</th>
                            <th>Particular</th>
                            <th>DR</th>
                            <th>CR</th>
                            <th>Comments</th>
                            <th>Description</th>
                        </tr>
                        <g:each in="${documents}" var="doc">
                            <tr>
                                <td></td>
                                <td>Ref: ${doc.reference}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <g:each in="${doc.transactions.sort{a,b -> b.postingKey.name <=> a.postingKey.name}}" var="transaction" status="i">
                                <tr>
                                    <td>
                                        <g:if test="${i == 0}">
                                           <g:formatDate format="MM/dd" date="${DateUtils.convertToDate(doc.documentDate)}"/>
                                        </g:if>
                                    </td>
                                    <td>${transaction.account.name}</td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.DR == Side.valueOf(transaction.postingKey.name)}">
                                            ${transaction.amount}
                                        </g:if>
                                    </td>
                                    <td style="text-align:right;">
                                        <g:if test="${Side.CR == Side.valueOf(transaction.postingKey.name)}">
                                            ${transaction.amount}
                                        </g:if>
                                    </td>
                                    <td>${transaction.comment}</td>
                                    <td>${transaction.description}</td>
                                </tr>
                            </g:each>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </g:each>
                        <tr>
                        </tr>
                    </table>
                </g:if>
                <g:else>
                    <p align="center">
                        <br/>
                        <b>Nothing to Show</b>
                        <br/>
                    </p>
                </g:else>
            </div>
    </body>
</html>