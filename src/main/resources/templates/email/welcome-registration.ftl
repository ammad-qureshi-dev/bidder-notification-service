<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Bidder</title>
</head>
<body style="margin:0; padding:0; background-color:#f4f5f7; font-family:'Segoe UI', Helvetica, Arial, sans-serif;">
<table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background-color:#f4f5f7; padding:32px 0;">
    <tr>
        <td align="center">
            <table role="presentation" width="480" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06);">
                <!-- Header -->
                <tr>
                    <td align="center" style="background:linear-gradient(135deg,#4f46e5,#7c3aed); padding:40px 24px;">
                        <div style="font-size:32px; line-height:1; margin-bottom:8px;">&#128296;</div>
                        <h1 style="margin:0; color:#ffffff; font-size:24px; font-weight:600;">Welcome to Bidder</h1>
                    </td>
                </tr>
                <!-- Body -->
                <tr>
                    <td style="padding:32px 32px 8px 32px;">
                        <p style="margin:0 0 16px 0; font-size:16px; color:#1f2937; line-height:1.5;">
                            Hi ${data.fullName}!
                        </p>
                        <p style="margin:0 0 24px 0; font-size:16px; color:#4b5563; line-height:1.6;">
                            Thanks for joining <strong>Bidder</strong>. Your account is ready to go &mdash; start
                            exploring live auctions or list your own items to see the bids roll in.
                        </p>
                    </td>
                </tr>
                <!-- CTA -->
                <tr>
                    <td align="center" style="padding:0 32px 32px 32px;">
                        <a href="${data.appUrl!'#'}" style="display:inline-block; background-color:#4f46e5; color:#ffffff; text-decoration:none; font-size:15px; font-weight:600; padding:14px 32px; border-radius:8px;">
                            Start Bidding
                        </a>
                    </td>
                </tr>
                <!-- Divider -->
                <tr>
                    <td style="padding:0 32px;">
                        <hr style="border:none; border-top:1px solid #e5e7eb; margin:0 0 24px 0;">
                    </td>
                </tr>
                <!-- Tips -->
                <tr>
                    <td style="padding:0 32px 32px 32px;">
                        <p style="margin:0 0 12px 0; font-size:14px; color:#6b7280; font-weight:600;">Get started in two ways:</p>
                        <p style="margin:0 0 8px 0; font-size:14px; color:#4b5563;">&#128176; Place a bid on an item you love</p>
                        <p style="margin:0; font-size:14px; color:#4b5563;">&#128230; Create an auction and sell your own items</p>
                    </td>
                </tr>
                <!-- Footer -->
                <tr>
                    <td align="center" style="background-color:#f9fafb; padding:20px 24px; border-top:1px solid #e5e7eb;">
                        <p style="margin:0; font-size:12px; color:#9ca3af;">Happy bidding! &mdash; The Bidder Team</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
