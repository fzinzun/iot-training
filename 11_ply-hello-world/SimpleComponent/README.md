# \<Simple-Component\>

This is a simple hello world component

## Install the Polymer-CLI

First, make sure you have the [Polymer CLI](https://www.npmjs.com/package/polymer-cli) installed. Then run `polymer serve` to serve your application locally.

## Viewing Your Application

```
$ polymer serve
```

## Building Your Application

```
$ polymer build
```

This will create a `build/` folder with `bundled/` and `unbundled/` sub-folders
containing a bundled (Vulcanized) and unbundled builds, both run through HTML,
CSS, and JS optimizers.

You can serve the built versions by giving `polymer serve` a folder to serve
from:

```
$ polymer serve build/bundled
```

## Running Tests

```
$ polymer test
```

Your application is already set up to be tested via [web-component-tester](https://github.com/Polymer/web-component-tester). Run `polymer test` to run your application's test suite locally.


##Integrate live Reload
1. npm init iniside your project directory to create a package.json file
2. npm install browser-syn --save-dev in the same directory
3. add astart script to package.json:
"scripts": {
    "dev": "polymer serve & npm run watch",
    "test": "polymer test",
    "watch": "browser-sync start --proxy localhost:8080 --files 'src/*.html, src/*.js, images/*' "
  },
4. Now just npm run dev 