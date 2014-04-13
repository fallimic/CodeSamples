package lsystem

import ("math" 
	"text/template"
	"os" 
	"encoding/json"
	"log"
	"io"
	"fmt"
)

type TurtleInterpreter struct {
	input []rune
	turtle *Turtle
	w io.Writer
	delta float64
	angleinc float64
	
}

type Turtle struct {
	x, y float64
	orientation float64
}

type Config struct {
	InitialX, InitialY float64
	Orientation float64
	AngleIncrement float64
	Displacement float64
	LineWidth float64
}

var config Config

func MakeTurtleInterpreter(input []rune, outputfile, configFile string) TurtleInterpreter {
	configfile, _ := os.Open(configFile)
	output, _ := os.Create(outputfile)
	defer configfile.Close()
	

	
	cdecode := json.NewDecoder(configfile)
	
	err := cdecode.Decode(&config)
	if (err != nil){
		log.Fatal(err.Error())  
	}


	return TurtleInterpreter{
		input: input,
		w: output,
		turtle: &Turtle{
			x: config.InitialX,
			y: config.InitialY,
			orientation: config.Orientation,
		},
	}
}

func (t *Turtle) IncOrientation(){
	t.orientation += config.AngleIncrement
}

func (t *Turtle) DecOrientation(){
	t.orientation -= config.AngleIncrement
}

func (t *Turtle) Step(){
	t.x += math.Cos(t.orientation) * config.Displacement
	t.y += math.Sin(t.orientation) * config.Displacement
}

type Command struct {
	X1, X2, Y1, Y2 float64
	LineWidth float64
}
func (t TurtleInterpreter) WriteFile() {
	commands := make([]Command, len(t.input))
	j := 0
	fmt.Println(t.input)
	for _, r := range t.input {
		if r == 'F' {
			x1, y1 := t.turtle.x, t.turtle.y
			t.turtle.Step()
			x2, y2 := t.turtle.x, t.turtle.y
			fmt.Printf("%f, %f, %f, %f\n", x1, x2, y1, y2)
			commands[j] = Command{
				X1: x1,
				X2: x2,
				Y1: y1,
				Y2: y2,
				LineWidth: config.LineWidth,
			}
			j++
		} else if r == '-' {
			t.turtle.DecOrientation()
		} else if r == '+' {
			t.turtle.IncOrientation()
		} else if r == 'f' {
			t.turtle.Step()
		}
		
	}
	svgtemplate := template.Must(template.ParseFiles("base.svg"))
	err := svgtemplate.Execute(t.w, commands[0:j])
	if (err != nil){
		log.Fatal(err.Error())
	}
}
