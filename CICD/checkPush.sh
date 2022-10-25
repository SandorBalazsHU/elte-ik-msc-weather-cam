lines=$(git diff --name-only main..dev | wc -l )
if [ $lines -eq 0 ]; then
    echo "FAIL"
fi