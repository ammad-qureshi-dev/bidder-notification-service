<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Password Was Changed</title>
</head>
<body style="margin:0; padding:0; background-color:#f4f5f7; font-family:'Segoe UI', Helvetica, Arial, sans-serif;">
<table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background-color:#f4f5f7; padding:32px 0;">
    <tr>
        <td align="center">
            <table role="presentation" width="480" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06);">
                <!-- Header -->
                <tr>
                    <td align="center" style="background:linear-gradient(135deg,#4f46e5,#7c3aed); padding:40px 24px;">
                        <div style="font-size:32px; line-height:1; margin-bottom:8px;">&#9989;</div>
                        <h1 style="margin:0; color:#ffffff; font-size:24px; font-weight:600;">Password Changed</h1>
                    </td>
                </tr>
                <!-- Body -->
                <tr>
                    <td style="padding:32px 32px 8px 32px;">
                        <p style="margin:0 0 16px 0; font-size:16px; color:#1f2937; line-height:1.5;">
                            Hi ${data.fullName}!
                        </p>
                        <p style="margin:0 0 24px 0; font-size:16px; color:#4b5563; line-height:1.6;">
                            Your <strong>Bidder</strong> account password was successfully updated. You can now use
                            your new password to sign in.
                        </p>
                    </td>
                </tr>
                <!-- Warning -->
                <tr>
                    <td style="padding:0 32px 32px 32px;">
                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background-color:#fef2f2; border:1px solid #fecaca; border-radius:8px;">
                            <tr>
                                <td style="padding:16px 20px;">
                                    <p style="margin:0; font-size:14px; color:#991b1b; line-height:1.5;">
                                        &#9888;&#65039; If you didn't make this change, your account may be compromised.
                                        Please <a href="${data.supportUrl!'#'}" style="color:#991b1b; font-weight:600;">contact us</a> immediately.
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!-- Footer -->
                <tr>
                    <td align="center" style="background-color:#f9fafb; padding:20px 24px; border-top:1px solid #e5e7eb;">
                        <p style="margin:0; font-size:12px; color:#9ca3af;">The Bidder Team</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
