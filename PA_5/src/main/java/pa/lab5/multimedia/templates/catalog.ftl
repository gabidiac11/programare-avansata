<html>
<head>
        </head>
        <body>

        <ul>
        <#list mediaList as mediaList>
            <li> ${mediaList.name} from ${mediaList.path}</li>
        </#list>
        </ul>

        </body>
        </html>