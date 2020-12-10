# Confirmation Prompt
echo "This script will kill all your running docker containers."
read -p "Are you sure? (y/N) " -n 1 -r
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
	echo "Exiting ..."
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1
fi
echo

# Read the output of the command and remove the first line
readarray -t A_CONTAINERS < <( sudo docker ps | sed '1d')

echo "[>] ${#A_CONTAINERS[*]} containers detected ..."

# Delimiter
IFS=' '
# Access each element of array
for i in "${A_CONTAINERS[@]}"; do
	# Split the string by IFS
	read -ra ADDR <<< "$i";
	# Kill container
	OUTPUT=$(sudo docker kill ${ADDR[0]});
	echo "[>] Killed container: $OUTPUT"
done

echo "[!] DONE"
