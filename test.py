import json

def lambda_handler(event, context):
    response = {
        "statusCode": 200,
        "body": json.dumps("AWS Lambda and S3 Bucket - New version")
    }
    return response
