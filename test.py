import json
import boto3
from botocore.exceptions import ClientError

def handler(event, context):
    # Get the secret value from AWS Secrets Manager
    secret_name = "POSTGRES_CREDS"
    region_name = "ap-south-1"

    # Create a Secrets Manager client
    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name=region_name
    )

    try:
        get_secret_value_response = client.get_secret_value(
            SecretId=secret_name
        )
        secret = get_secret_value_response['SecretString']
        secret_dict = json.loads(secret)
        db_password = secret_dict['password']  # Replace 'password' with the actual key in your secret JSON
    except ClientError as e:
        return {
            'statusCode': 500,
            'body': json.dumps(f"Error retrieving secret: {str(e)}")
        }

    return {
        'statusCode': 200,
        'body': json.dumps(f"DB Password: {db_password}")
    }