using System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace WindowsAsync
{
    /// <summary>
    /// As simple window with a button a two text fields
    /// </summary>
    internal class TestUi : Window
    {
        private Button button = new Button {Content = "UpdateUI"};
        private TextBlock primes = new TextBlock();
        private TextBlock currentTime = new TextBlock();

        public TestUi()
        {
            InitiailizeComponents();
        }

        private void InitiailizeComponents()
        {
            button.Width = 100;
            button.Height = 30;

            Width = 300;
            Height = 300;
            var panel = new StackPanel();
            panel.Children.Add(button);
            panel.Children.Add(primes);
            panel.Children.Add(currentTime);

            Content = panel;

            button.Click += (sender, args) => UpdateUi();
        }

        /// <summary>
        /// Updates the UI
        /// </summary>
        private async void UpdateUi()
        {
            // Does some long running task, this blocks the display of the current time
            // TODO: make this call asynchronous
            await DisplayNumberOfPrimes();
            // shows the current time
            Console.WriteLine("show current time call");
            await ShowCurrentTime();

        }

        /// <summary>
        /// calculates the prima in five different ranges and displays them in the GUI
        /// </summary>
        private async Task DisplayNumberOfPrimes()
        {
            for (int i = 1; i < 5; i++)
            {
                var pr = await GetPrimesCount(i*100000, 1000000);
                primes.Text += pr
                    + " primes between " + (i*100000) + "and " + ((i+1) * 100000-1)
                    + Environment.NewLine;
                
            }
        }

        /// <summary>
        /// Prints the current time into the primes-Textblock (GUI)
        /// </summary>
        private async Task ShowCurrentTime()
        {
            for (int i = 1; i < 5; i++)
            {
                primes.Text += DateTime.Now + Environment.NewLine;
                await Task.Delay(1000);
                //Thread.Sleep(1000);
            }

        }

       /// <summary>
       /// calculates the number of primes in the specified range
       /// </summary>
       /// <param name="start">start number</param>
       /// <param name="count">range value, beginning from start</param>
       /// <returns></returns>
        private Task<int> GetPrimesCount(int start, int count)
        {
            return Task.Run(() => ParallelEnumerable.Range(start, count)
                                  .Count(n => Enumerable.Range(2, (int) Math.Sqrt(n) - 1).All(i => n%i > 0)));
        }
    }
}