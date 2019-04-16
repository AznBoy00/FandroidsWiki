infile = r"log.txt"

important = []
keep_phrases = ["BUILD FAILED",
              "Execution failed",
              "keep me"]

with open(infile) as f:
    f = f.readlines()

for line in f:
    for phrase in keep_phrases:
        if phrase in line:
            important.append(line)
            break

print(important)