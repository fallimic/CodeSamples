package main 

import (
	"fmt"
)

func main() {
	arr := []int{1, 2, 3, 4, 5, 6, 7, 8}
	ch := make(chan int)
	go sum(arr, ch)
	fmt.Println(<-ch)
}

func sum(data []int, returnCh chan int) {
	if len(data) == 1 {
		returnCh <- data[0]
	} else {
		mid := len(data) / 2
		c := make(chan int, 2)
		go sum(data[:mid], c)
		sum(data[mid:], c)
		x,y := <-c, <-c
		returnCh <- x + y
		
	}
}
