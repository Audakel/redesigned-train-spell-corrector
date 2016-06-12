# spell-corrector
You are familiar with spell checkers. For most spell checkers, a candidate word is considered to
be spelled correctly if it is found in a long list of valid words called a dictionary. Google provides
a more powerful spell corrector for validating the keywords we type into the input text box. It not
only checks against a dictionary, but, if it doesn’t find the keyword in the dictionary, it suggests
a most likely replacement. To do this it associates with every word in the dictionary a frequency,
the percent of the time that word is expected to appear in a large document. When a word is
misspelled (i.e. it is not found in the dictionary) Google suggests a “similar” word (“similar” will
be defined later) whose frequency is larger or equal to any other “similar” word.
In this project you will create such a spell corrector. There is one major difference. Our spell
checker will only validate a single word rather than each word in a list of words.
