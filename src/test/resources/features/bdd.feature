Feature: Assemble unfinished goods

Scenario: Assemble 3 products with unfinished goods from conveyer belt
Given there are 3 machines and 6 bolts in conveyer belt
When each worker take 60 seconds to assemble one product
Then the total product created will be 3
And the total time taken will be 60 seconds






