# web-log-analyzer
Hadoop exercise 2
## How to run
1. Use `mvn assenbly:assembly` to build jar file
2. Run `hadoop jar <location of jar> com.alviss.hadoop.BrowserCount <log file path> <destination>
`
## Note
1. If maven is used in assembling, the parameter starts from args[0].