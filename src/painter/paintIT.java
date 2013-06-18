package painter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class paintIT
{
    public static void main(String[] args)
    {
        PaintWindow frame = new PaintWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}

class PaintWindow extends JFrame
{
    public PaintWindow()
    {
        setTitle("PaintIt");
        setSize(450, 450);

        panel = new JPanel();
        drawPad = new PadDraw();

        panel.setPreferredSize(new Dimension(32, 68));

        //создать новый контейнер
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        //установить панель слева, а место для рисования по центру
        content.add(panel, BorderLayout.WEST);
        content.add(drawPad, BorderLayout.CENTER);

//add the color buttons:
        makeColorButton(Color.BLUE);
        makeColorButton(Color.MAGENTA);
        makeColorButton(Color.RED);
        makeColorButton(Color.GREEN);
        makeColorButton(Color.BLACK);

        //создать кнопку для очистки
        JButton clearButton = new JButton("Сброс");
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                drawPad.clear();
            }
        });
        panel.add(clearButton);
    }

    /*
    * кнопка для замены цвета
    */
    public void makeColorButton(final Color color)
    {
        JButton tempButton = new JButton();
        tempButton.setBackground(color);
        tempButton.setPreferredSize(new Dimension(16, 16));
        panel.add(tempButton);
        tempButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                drawPad.changeColor(color);
            }
        });
    }

    private JPanel panel;
    private PadDraw drawPad;
}

class PadDraw extends JComponent
{
    //сюда будем "рисовать"
    Image image;
    //Это чем будем рисовать
    Graphics2D graphics2D;
    //Это будут координаты мышки
    int currentX, currentY, oldX, oldY;

    public PadDraw()
    {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter()
        {
            //если кнопка мышки нажата
            //то запоминаем старые коордианты
            public void mousePressed(MouseEvent e)
            {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            //пока кнопка мышки нажата
            //сводим линией старые и новые координаты
            public void mouseDragged(MouseEvent e)
            {
                currentX = e.getX();
                currentY = e.getY();

                graphics2D.drawLine(oldX, oldY, currentX, currentY);
                repaint();

                oldX = currentX;
                oldY = currentY;
            }
        });
    }

    //рисовалка
    //создаёт пустое изображение
    //устанавливает рендер
    //очищает картинку
    //и потом рисует изображение
    public void paintComponent(Graphics g)
    {
        if(image == null)
        {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D)image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    //это очистка изображения
    //цвет устанавливаеться = белый
    //окно заполняеться белым цветом
    //потом цвет устанавливаеться = чёрный
    public void clear()
    {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }

    public void changeColor(Color theColor)
    {
        graphics2D.setPaint(theColor);
        repaint();
    }
}