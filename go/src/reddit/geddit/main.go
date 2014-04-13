package main

import (
	"fmt"
	"log"
	"github/fallimic/reddit"
)

func main() {

	items, err := reddit.Get("aww")
	if err != nil {
		log.Fatal(err)
	}

	for _, item := range items {
		fmt.Println(item)
		fmt.Println()
	}
}

