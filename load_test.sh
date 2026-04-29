#!/bin/bash

# Simple load test to trigger the rate limiter
# It sends 10 requests to the /guard/ingest endpoint from the same IP

# First get a token for the admin user
echo "Getting admin token..."
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin", "password":"password"}' | grep -o '"token":"[^"]*' | grep -o '[^"]*$')

if [ -z "$TOKEN" ]; then
  echo "Failed to get token. Make sure the app is running."
  exit 1
fi

echo "Starting massive concurrent load test (100 requests)..."
echo "Requests are sent in parallel with randomized IP addresses to bypass rate limits."

# Record start time
START_TIME=$(date +%s)

for i in {1..100}
do
   # Generate a random IP address like 192.168.1.X
   RANDOM_IP="192.168.1.$((RANDOM % 254 + 1))"
   
   curl -s -o /dev/null -w "Request $i (IP: $RANDOM_IP) -> HTTP %{http_code} | Time: %{time_total}s\n" -X POST http://localhost:8080/guard/ingest \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d "{
       \"cardNo\": \"1234567890123456\",
       \"amount\": 500,
       \"currency\": \"NGN\",
       \"merchantId\": \"load-test-merchant\",
       \"senderId\": \"sender-1\",
       \"ipAddress\": \"$RANDOM_IP\"
     }" &
done

# Wait for all background curl processes to finish
wait

# Record end time
END_TIME=$(date +%s)
TOTAL_TIME=$((END_TIME - START_TIME))

echo ""
echo "=========================================="
echo "          LOAD TEST COMPLETE              "
echo "=========================================="
echo "Total Requests: 100"
echo "Total Time Taken: ${TOTAL_TIME} seconds"
echo "Approx. Throughput: $((100 / (TOTAL_TIME > 0 ? TOTAL_TIME : 1))) req/sec"
echo "=========================================="
