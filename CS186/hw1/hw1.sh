#!/bin/bash
# bash command-line arguments are accessible as $0 (the bash script), $1, etc.
# echo "Running" $0 "on" $1
# $1 is the folder

echo "file_name,from,to,cc,subject,date,message_id,body" > mail.csv
rm -f tokens.csv
ruby extract_mail.rb $1
sort tokens.csv | uniq --count | sed 's/^ *//;s/ *$//;s/\ [0-9a-zA-Z._@]*,/,/' | awk 'BEGIN {FS=OFS=","} {temp=$1; $1=$2; $2=temp} {print}' | awk -F, '{a[$1]+=$2}END{for(i in a) print i "," a[i]}' > token_counts.csv
sed -i '1s/^/token,count\n/' token_counts.csv
sed -i '1s/^/message_id,token\n/' tokens.csv
echo "token,count" > state_counts.csv
states=("alabama" "alaska" "arizona" "arkansas" "california" "colorado" "connecticut" "delaware" "florida" "georgia" "hawaii" "idaho" "illinois" "indiana" "iowa" "kansas" "kentucky" "louisiana" "maine" "maryland" "massachusetts" "michigan" "minnesota" "mississippi" "missouri" "montana" "nebraska" "nevada" "hampshire" "jersey" "mexico" "york" "dakota" "ohio" "oklahoma" "oregon" "pennsylvania" "rhode" "carolina" "tennessee" "texas" "utah" "vermont" "virginia" "washington" "wisconsin" "wyoming")
for state in "${states[@]}"
do 
  cat token_counts.csv | grep "^$state," >> state_counts.csv
done
exit 0
