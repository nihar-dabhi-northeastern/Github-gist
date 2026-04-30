# Polkadot Score

Counts the polkadots on Angelica's ASCII dress and spits out a score.

## The formula

```
Score = (polkadots outside lips) + (polkadots inside lips) * (pupil chars)
```

## How I solved it

Pretty straightforward once you read the art carefully:

1. Find the lips — look for a `(-)` pattern in the art and grab its start/end column.
2. Count the pupils — find the eyes row (the one with `•` characters) and count how many bullets are on it.
3. Walk through every line, every char. If it's a polkadot (`0`, `o`, or `O`), check if its column is inside the lip range or outside. Bump the right counter.
4. Plug into the formula.

## The catch

This art uses capital `O` for the polkadots, not `0`. Took me a sec to notice. Also there are a bunch of `()` patterns in the art that look like lips but aren't — they're decoration on the arms and the title. Need to be careful which one you pick.

## Run it

```
javac PolkadotScore.java
java PolkadotScore
```

`main` has a small test case so you can see it works before throwing the real art at it.
