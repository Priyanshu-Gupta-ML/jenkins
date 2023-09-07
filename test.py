import json

def handler(event, context):
    return {
        'statusCode': 200,
        'body': json.dumps("AWS Lambda and S3 Bucket - New version")
    }