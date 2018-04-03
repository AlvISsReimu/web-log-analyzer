# web-log-analyzer
Count different browsers' occurrence from 10K user visit log.

## How to run
1. Use `mvn assembly:assembly` to build jar file
2. Run `hadoop jar <location of jar> com.alviss.hadoop.BrowserCount <log file path> <destination>
`
## Note
1. The parameter starts from `args[0]` if run in shell script.
2. Do not need to delete `META-INF/LICENSE` from jar if using maven to assemble it.
