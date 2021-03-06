# v2.2

- soft-forkability for cost estimation (#503)
- optimization of cost estimation rules (#523)
- new Context parameters (initCost, costLimit)
- implemented fast complexity measure of box propositions
  (to fail-fast on too complex scripts #537)
- implemented test cases to check fast script rejections of
  of either oversized of too costly scripts  
  
# v2.1
- soft-forkability for language evolution (add types, operations) #500
- ErgoTree language specification (abstract syntax, typing rules, semantics, serialization format) #495 
- ErgoTree IR extended with metadata to generate specification appendixes.
- more operations implemented (zip #498, element-wise xor for collections #494, flatMap #493, 
  plusModQ, minusModQ #488, logical xor #478, filter #471, Option.map #469, groupGenerator #440)
- security improvements (like limiting ErgoTree depth during deserialization #459 #482)
- ICO and LETS examples #450   
- final version of ErgoScript white paper (#410)
- improvements in scorex-util serialization
- various fixes code cleanup and better test coverage (#504, #508, #515)