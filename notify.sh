#!/bin/bash
STATUS=$1

# Determine color based on pipeline status (Green for success, Red for fail)
if [ "$STATUS" == "success" ]; then
  COLOR="65280"   # Decimal for Green
  EMOJI="✅"
else
  COLOR="16711680" # Decimal for Red
  EMOJI="❌"
fi

# Construct Discord Webhook JSON payload
PAYLOAD=$(cat <<EOF
{
  "embeds": [
    {
      "title": "$EMOJI Pipeline Update (API Automation)",
      "description": "**Pipeline ID:** $CI_PIPELINE_ID\n**Status:** $STATUS",
      "url": "$CI_PIPELINE_URL",
      "color": $COLOR,
      "fields": [
        {
          "name": "Project",
          "value": "Restful-Booker API",
          "inline": true
        },
        {
          "name": "Branch",
          "value": "$CI_COMMIT_REF_NAME",
          "inline": true
        },
        {
          "name": "View Report",
          "value": "[Click here to view Allure Report](https://capstone_yah.gitlab.io/restful-booker-api-automation)",
          "inline": false
        }
      ],
      "footer": {
        "text": "Triggered by $GITLAB_USER_NAME"
      }
    }
  ]
}
EOF
)

# Send request to Discord
curl -H "Content-Type: application/json" -d "$PAYLOAD" "$DISCORD_WEBHOOK_URL"
