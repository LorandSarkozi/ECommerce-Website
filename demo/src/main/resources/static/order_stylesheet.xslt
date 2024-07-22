<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/order">
        <html>
            <body>
                <h2>Order Details</h2>
                <p><strong>Tracking ID:</strong> <xsl:value-of select="trackingId"/></p>
                <p><strong>User Name:</strong> <xsl:value-of select="userName"/></p>
                <p><strong>Address:</strong> <xsl:value-of select="address"/></p>
                <p><strong>Amount:</strong> <xsl:value-of select="amount"/></p>
                <p><strong>Status:</strong> <xsl:value-of select="orderStatus"/></p>
                <p><strong>Date:</strong> <xsl:value-of select="date"/></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>