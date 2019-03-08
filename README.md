# Brand Mapping

Q-Gram String Comparison Algorithm
It is basically a Java web application based on Spring Boot.
There come into the following four types of the string comparison algorithms to compute the mapping rate of arbitrary two strings:
1. Keyword Priority
2. LCS (Longest Common String)
3. Levenshtein
4. Q-Gram

## Illustration of Q-Gram Algorithm
For q-gram algorithm, q stands for the length of the partial string to break a brand's name into.
For instance, 3-gram for "Chemical" is {che, hem, emi, mic, ica, cal}.

To initialize the fuzzy string match application, an **inverted list** of all the brands is firstly loaded into memory, and the inverted list is in the form of a hash map as below:

| Partial String      | Set of Brands |
| ----------- | ----------- |
| che      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc., Chevron       |
| hem      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc.        |
| emi      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc.        |
| mic      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc.        |
| ica      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc.        |
| cal      | Dow Chemical, Eastman Chemical Company, Air Products & Chemicals Inc.        |



To search for the top-k matches ordered by the matching rate from the highest to the lowest, it takes three steps:
1. For any matched partial string, a **counter map** with the individual brand mapped to the occurring counts is built;
2. For the matching rate between the searched keyword and all the brands in the counter map, a **tree map** is built ordered by the matching rate;
3. pop up the top-k matches from the tree map. 

For example, when the searched keyword is Chemical, a counter map will be created as below:

| Brand      | Counts |
| ----------- | ----------- |
| Dow Chemical                  | 6 |
| Eastman Chemical Company      | 6 |
| Air Products & Chemicals Inc. | 6 |
| Chevron                       | 1 |


To compute the matching rate between "Chemical" and "Dow Chemical", the equation is as below:

Matching rate = rate1 x 0.8 + rate2 x 0.2;

rate1 = Counts / Brand's string length - q + 1; (default q-gram is set as q = 3)

rate2 = Minimum string length of the searched keyword and Brand / Maximum string length of the searched keyword and Brand;


